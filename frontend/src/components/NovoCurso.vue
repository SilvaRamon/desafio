<template>
  <div>
    <v-form v-model="validar">
      <v-container fluid>
        <v-layout row wrap>
          
          <v-flex xs12 class="headline font-weight-bold">
              {{ titulo }}
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-model="codigo"
              :rules="regrasCodigo"
              :counter="9"
              :mask="mascaraCodigo"
              label="Código do aluno"
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
          <v-flex xs12 md12 text-xs-right pt-2>
            <v-btn to="/cursos" dark color="primary">
              <v-icon>arrow_back</v-icon> Voltar
            </v-btn>
            <v-btn dark color="primary" @click="setCurso">
              <v-icon>library_add</v-icon> Cadastrar
            </v-btn>
            <v-btn dark color="primary">
              <v-icon>clear</v-icon> Limpar
            </v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-form>
  </div>
</template>

<script>
export default {
  name: 'NovoCurso',
  data() {
    return {
      titulo: 'Cadastrar novo curso',
      validar: false,
      codigo: '',
      mascaraCodigo: '#########',
      nome: '',
      cargaHoraria: '',
      mascaraCargaHoraria: '####',
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
      ]
    };
  },
  methods: {
    setCurso() {
      console.log("AQUI");
      axios.post('http://localhost:3000/api/cursos', {
          codigo: this.codigo,
          nome: this.nome,
          cargaHoraria: this.cargaHoraria
        })
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
};
</script>
