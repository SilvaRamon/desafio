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
                  <v-btn flat icon color="red" title="Remover"
                    @click="deleteCurso(props.item)"
                  >
                    <v-icon>delete</v-icon>
                  </v-btn>
                  <v-btn flat icon color="indigo" title="Editar">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-dialog
                    v-model="dialog"
                    persistent
                    width="100%"
                  >
                    <template v-slot:activator={on}>
                      <v-btn
                        flat icon color="black"
                        title="Alunos do curso"
                        v-on="on"
                      >
                        <v-icon>people</v-icon>
                      </v-btn>
                    </template>

                    <v-card>
                      <v-card-title
                        class="headline grey lighten-2"
                        primary-title
                      >
                        Lista de alunos
                      </v-card-title>
                      <v-card-text>
                        <v-data-table
                          :headers="alunoHeaders"
                          :items="props.item.alunos"
                        >
                          <template v-slot:items="props">
                            <td>{{ props.item.codigo }}</td>
                            <td class="text-xs-left">{{ props.item.nome }}</td>
                            <td class="text-xs-left">{{ props.item.cpf }}</td>
                            <td class="text-xs-left">{{ props.item.endereco }}</td>
                            <td class="text-xs-left">{{ props.item.cep }}</td>
                            <td class="text-xs-left">{{ props.item.email }}</td>
                            <td class="text-xs-left">{{ props.item.telefone }}</td>
                          </template>
                        </v-data-table>
                      </v-card-text>
                      <v-card-actions class="text-xs-right">
                        <v-btn
                          color="primary"
                          flat
                          @click="dialog = false"
                        >
                          Fechar
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
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
      alunoHeaders: [
        {
          text: 'Código',
          align: 'left',
          sortable: false,
          value: 'codigo'
        },
        { text: 'Nome', value: 'nome' },
        { text: 'CPF', value: 'cpf' },
        { text: 'Endereço', value: 'endereco' },
        { text: 'CEP', value: 'cep' },
        { text: 'E-mail', value: 'email' },
        { text: 'Telefone', value: 'telefone' }
      ],
      cursos: [],
    };
  },
  methods: {
    getCursos() {
      axios.get('http://localhost:3000/api/cursos')
        .then(response => {
          this.cursos = response.data;
        })
        .catch(error => console.log(error));
    },
    deleteCurso(obj) {
      axios.delete('http://localhost:3000/api/cursos/' + obj.id)
        .then(response => {
          this.cursos.splice(this.cursos.indexOf(obj), 1);
          console.log(response.status);
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  },
  mounted() {
    this.getCursos();
  },
};
</script>
