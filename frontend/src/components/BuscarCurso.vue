<template>
  <div>
    <v-container grid-list-md text-xs-left>
      <v-layout row wrap>
        <v-flex xs12>
          <v-card>
            <v-card-title class="headline font-weight-bold">
              Lista de cursos
              <v-spacer/>
              <v-text-field
                v-model="search"
                append-icon="search"
                label="Buscar"
                single-line
                hide-details
              ></v-text-field>
            </v-card-title>
            <v-data-table
              :headers="headers"
              :items="cursos"
              :search="search"
            >
              <template v-slot:items="props">
                <td>{{ props.item.codigo }}</td>
                <td class="text-xs-left">{{ props.item.nome }}</td>
                <td class="text-xs-left">{{ props.item.dataCadastro }}</td>
                <td class="text-xs-left">{{ props.item.cargaHoraria }} h</td>
                <td class="text-xs-left">
                  <v-btn flat icon color="red" title="Remover">
                    <v-icon>delete</v-icon>
                  </v-btn>
                  <v-btn flat icon color="indigo" title="Editar">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn flat icon color="black"  title="Detalhar">
                    <v-icon>more_vert</v-icon>
                  </v-btn>
                </td>
              </template>
              <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                Sua busca por "{{ search }}" não obteve resultados.
              </v-alert>
            </v-data-table>
            <div class="text-xs-right">
              <v-btn dark color="primary" to="/cursos/novo">
                <v-icon>add</v-icon> Novo Curso
              </v-btn>
            </div>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'BuscarCurso',
  data() {
    return {
      titulo: 'Buscar cursos',
      dialog: false,
      search: '',
        headers: [
        {
          text: 'Código',
          align: 'left',
          sortable: false,
          value: 'codigo'
        },
        { text: 'Nome', value: 'nome' },
        { text: 'Data de Cadastro', value: 'dataCadastro' },
        { text: 'Carga Horária (Horas)', value: 'cargaHoraria' },
        { text: 'Ações', value: ''}
      ],
      cursos: [],
    };
  },
  methods: {
    getCursos() {
      axios.get('http://localhost:3000/api/cursos')
        .then(response => {
          this.cursos = response.data;
          console.log(this.cursos);
        })
        .catch(error => console.log(error));
    }
  },
  mounted() {
    this.getCursos();
  },
};
</script>
