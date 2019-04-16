<template>
  <div>
    <v-form 
      ref="form"
      v-model="valid"
      lazy-validation
    >
      <v-container fluid>
        <v-layout row wrap>
          <v-flex xs12 class="headline font-weight-bold">
            <!-- Edit é uma prop passada pela rota -->
            {{ edit ? 'Editar curso ' : titulo }}
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="codigo"
              :rules="regrasCodigo"
              :counter="9"
              :mask="mascaraCodigo"
              label="Código do curso"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="nome"
              :rules="regrasNome"
              :counter="60"
              label="Nome do curso"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="cargaHoraria"
              :rules="regrasCargaHoraria"
              :mask="mascaraCargaHoraria"
              :counter="4"
              label="Carga Horária"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="dataCadastro"
              :rules="regrasDataCadastro"
              :mask="mascaraData"
              :counter="8"
              label="Data de cadastro"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md12 text-xs-right pt-2>
            <v-btn to="/cursos" dark color="primary">
              <v-icon>arrow_back</v-icon> Voltar
            </v-btn>
            <v-btn dark color="primary" @click="validar">
              <v-icon>
                {{ edit ? 'save' : 'library_add' }}
              </v-icon>
              {{ edit ? 'Salvar' : 'Cadastrar' }}
            </v-btn>
            <v-btn dark color="primary" @click="limpar">
              <v-icon>clear</v-icon> Limpar
            </v-btn>
          </v-flex>
          <v-flex>
            <v-snackbar
              v-model="snackbar"
              :color="snackbarColor"
              :multi-line="true"
              :timeout="3000"
              :vertical="false"
            >
              {{ snackbarMsg }}
              <v-btn dark flat @click="snackbar = false">
                Fechar
              </v-btn>
            </v-snackbar>
          </v-flex>
        </v-layout>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'NovoCurso',
  props: ['edit', 'id_curso'],
  data() {
    return {
      titulo: 'Cadastrar novo curso',
      valid: true,
      codigo: '',
      mascaraCodigo: '#########',
      nome: '',
      cargaHoraria: '',
      mascaraCargaHoraria: '####',
      dataCadastro: '',
      mascaraData: '##/##/####',
      regrasCodigo: [
        v => !!v || 'Código é obrigatório.',
        v => v.length <= 9 || 'Código deve ter no máximo 9 caracteres.'
      ],
      regrasNome: [
        v => !!v || 'Nome é obrigatório.',
        v => v.length <= 60 || 'Nome deve ter menos de 60 caracteres.'
      ],
      regrasCargaHoraria: [
        v => !!v || 'Carga Horária é obrigatória.',
        v => v.length <= 4 || 'Carga Horária deve ter no máximo 4 caracteres.'
      ],
      regrasDataCadastro: [
        v => !!v || 'Data de cadastro é obrigatória',
        v => v.length === 8 || 'Data de cadastro deve ter exatamente 8 caracteres.'
      ],
      snackbar: false,
      snackbarColor: '',
      snackbarMsg: ''
    };
  },
  methods: {
    setCurso() {
      let dataRegex = /(\d{2})(\d{2})(\d{4})/g;
      axios.post('http://localhost:3000/api/cursos', {
        codigo: this.codigo,
        nome: this.nome,
        cargaHoraria: this.cargaHoraria,
        dataCadastro: this.dataCadastro.replace(dataRegex, "$1/$2/$3")
      })
      .then(response => {
        this.snackbar = true;
        this.snackbarColor = 'success';
        this.snackbarMsg = 'Cadastro realizado com sucesso!';
      })
      .catch(error => {
        this.snackbar = true;
        this.snackbarColor = 'error';
        this.snackbarMsg = 'Ocorreu um erro!';
      });
    },
    updateCurso() {
      let dataRegex = /(\d{2})(\d{2})(\d{4})/g;
      axios.put('http://localhost:3000/api/cursos/'+this.$props.id_curso, {
        id: this.$props.id_curso,
        codigo: this.codigo,
        nome: this.nome,
        cargaHoraria: this.cargaHoraria,
        dataCadastro: this.dataCadastro.replace(dataRegex, "$1/$2/$3")
      })
      .then(response => {
        this.snackbar = true;
        this.snackbarColor = 'success';
        this.snackbarMsg = 'Cadastro realizado com sucesso!';
      })
      .catch(error => {
        this.snackbar = true;
        this.snackbarColor = 'error';
        this.snackbarMsg = 'Ocorreu um erro!';
      });
    },
    getCurso() {
      axios.get('http://localhost:3000/api/cursos/'+this.$props.id_curso)
      .then(response => {
        // Resgatando os dados da requisicão
        this.codigo = response.data.codigo;
        this.nome = response.data.nome;
        this.cargaHoraria = response.data.cargaHoraria;
        this.dataCadastro = response.data.dataCadastro;
      })
      .catch(error => { console.log(error.response) });
    },
    validar() {
      if (this.$refs.form.validate()) {
        if( ! this.$props.edit) {
          this.setCurso();
        }{
          this.updateCurso();
        }
        this.$router.push({name: 'BuscarCurso'});
        this.limpar();
      }
    },
    limpar() {
      this.$refs.form.reset();
    }
  },
  mounted() {
    this.getCurso();
  }
};
</script>
