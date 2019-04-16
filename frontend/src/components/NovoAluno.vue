<template>
  <div>
    <v-form
      v-model="valid"
      ref="form"
    >
      <v-container fluid>
        <v-layout row wrap>
          <v-flex xs12 class="headline font-weight-bold">
              {{ edit ? 'Editar aluno' : titulo }}
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="codigo"
              :rules="regrasCodigo"
              :mask="mascaraCodigo"
              :counter="9"
              label="Código do aluno"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="nome"
              :rules="regrasNome"
              :counter="60"
              label="Nome completo"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="cpf"
              :rules="regrasCpf"
              :counter="11"
              :mask="mascaraCpf"
              label="CPF"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="endereco"
              :rules="regrasEndereco"
              :counter="120"
              label="Endereço"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="cep"
              :rules="regrasCep"
              :mask="mascaraCep"
              :counter="8"
              label="CEP"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="email"
              :rules="regrasEmail"
              label="E-mail"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="telefone"
              :rules="regrasTelefone"
              :mask="mascaraTelefone"
              label="Telefone"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 md4>
            <v-select
              v-model="idCurso"
              :items="cursos"
              item-text="nome"
              item-value="id"
              label="Selecione o curso"
              :rules="regrasCurso"
              required
            ></v-select>
          </v-flex>
          <v-flex xs12 md12 text-xs-right pt-2>
            <v-btn to="/alunos" dark color="primary">
              <v-icon>arrow_back</v-icon> Voltar
            </v-btn>
            <v-btn dark color="primary" @click="validar">
              <v-icon>
                {{ edit ? 'save' : 'library_add'}}
              </v-icon>
              {{ edit ? 'Salvar' : 'Cadastrar'}}
            </v-btn>
            <v-btn dark color="primary">
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
  name: 'NovoAluno',
  props: ['edit', 'id_aluno'],
  data() {
    return {
      titulo: 'Cadastrar novo aluno',
      valid: true,
      codigo: '',
      mascaraCodigo: '#########',
      nome: '',
      cpf: '',
      mascaraCpf: '###.###.###-##',
      endereco: '',
      cep: '',
      mascaraCep: '#####-###',
      email: '',
      telefone: '',
      mascaraTelefone: '(##) #####-####',
      idCurso: '',
      regrasCodigo: [
        v => !!v || 'Código é obrigatório.',
        v => v.length <= 9 || 'Código deve ter no máximo 9 dígitos.'
      ],
      regrasNome: [
        v => !!v || 'Nome é obrigatório.',
        v => v.length <= 60 || 'Nome deve ter menos de 60 caracteres.'
      ],
      regrasCpf: [
        v => !!v || 'CPF é obrigatório.',
        v => v.length === 11 || 'CPF deve ter exatamente de 11 dígitos.'
      ],
      regrasEndereco: [
        v => !!v || 'Endereço é obrigatório.',
        v => v.length <= 120 || 'Endereço deve até exatamente de 120 caracteres.'
      ],
      regrasCep: [
        v => !!v || 'CEP é obrigatório.',
        v => v.length === 8 || 'CEP deve conter exatamente 8 dígitos.'
      ],
      regrasEmail: [
        v => !!v || 'Email é obrigatório.',
        v => /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Invalid e-mail.'
      ],
      regrasTelefone: [
        v => !!v || 'Telefone é obrigatório.',
        v => v.length === 11 || 'Telefone deve conter exatamente 11 dígitos.'
      ],
      regrasCurso: [
        v => !!v || 'Curso é obrigatório.'
      ],
      cursos: [],
      snackbar: false,
      snackbarColor: '',
      snackbarMsg: ''
    };
  },
  methods: {
    setAluno() {
      let cpfRegex = /(\d{3})(\d{3})(\d{3})(\d{2})/g;
      let cepRegex = /(\d{5})(\d{3})/g;
      let telRegex = /(\d{2})(\d{5})(\d{4})/g;

      axios.post(
        'http://localhost:3000/api/alunos/'+
          this.idCurso+'/curso',
      {
        codigo: this.codigo,
        nome: this.nome,
        cpf: this.cpf.replace(cpfRegex, "$1.$2.$3-$4"),
        endereco: this.endereco,
        cep: this.cep.replace(cpfRegex, "$1-$2"),
        email: this.email,
        telefone: this.telefone.replace(telRegex, "($1) $2-$3")
      })
      .then(response => {
        this.snackbar = true;
        this.snackbarColor = 'success';
        this.snackbarMsg = 'Cadastro realizado com sucesso!';
      })
      .catch(error => {
        this.snackbar = true;
        this.snackbarColor = 'error';
        this.snackbarMsg = 'Ocorreu um erro! ' + error.response;
      });
    },
    updateAluno() {
      let cpfRegex = /(\d{3})(\d{3})(\d{3})(\d{2})/g;
      let cepRegex = /(\d{5})(\d{3})/g;
      let telRegex = /(\d{2})(\d{5})(\d{4})/g;

      axios.put('http://localhost:3000/api/alunos/'+
        this.$props.id_aluno+'/curso/'+this.idCurso,
      {
        codigo: this.codigo,
        nome: this.nome,
        cpf: this.cpf.replace(cpfRegex, "$1.$2.$3-$4"),
        endereco: this.endereco,
        cep: this.cep.replace(cpfRegex, "$1-$2"),
        email: this.email,
        telefone: this.telefone.replace(telRegex, "($1) $2-$3")
      })
      .then(response => {
        this.snackbar = true;
        this.snackbarColor = 'success';
        this.snackbarMsg = 'Cadastro realizado com sucesso!';
      })
      .catch(error => {
        this.snackbar = true;
        this.snackbarColor = 'error';
        this.snackbarMsg = 'Ocorreu um erro! ' + error.response;
      });
    },
    validar() {
      if (this.$refs.form.validate()) {
        if ( ! this.$props.edit) {
          this.setAluno();
        } else {
          this.updateAluno();
        }
        this.limpar();
      }
    },
    limpar() {
      this.$refs.form.reset();
    },
    getCursos() {
      axios.get('http://localhost:3000/api/cursos')
        .then(response => {
        this.cursos = response.data;
      })
      .catch(error => {
        console.log(error.response);
      });
    },
    getAluno() {
      axios.get('http://localhost:3000/api/alunos/'+this.$props.id_aluno)
        .then(response => {
          this.id = response.data.id;
          this.codigo = response.data.codigo;
          this.nome = response.data.nome;
          this.cpf = response.data.cpf;
          this.endereco = response.data.endereco;
          this.cep = response.data.cep;
          this.email = response.data.email;
          this.telefone = response.data.telefone;
          console.log(response.data);
        })
        .catch(error => {
          console.log(error.response);
        });
    },
  },
  mounted() {
    this.getCursos();

    if (this.$props.edit) {
      this.getAluno();
    }
  }
};
</script>
