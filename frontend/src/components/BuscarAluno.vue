<template>
  <div>
    <v-container grid-list-md text-xs-left>
      <v-layout row wrap>
        <v-flex xs12>
          <v-card>
            <v-card-title class="headline font-weight-bold">
              Lista de alunos
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
              :items="alunos"
              :search="search"
            >
              <template v-slot:items="props">
                <td>{{ props.item.codigo }}</td>
                <td class="text-xs-left">{{ props.item.nome }}</td>
                <td class="text-xs-left">{{ props.item.cpf }}</td>
                <td class="text-xs-left">{{ props.item.endereco }}</td>
                <td class="text-xs-left">{{ props.item.cep }}</td>
                <td class="text-xs-left">{{ props.item.email }}</td>
                <td class="text-xs-left">{{ props.item.telefone }}</td>
                <td class="text-xs-left">
                  <v-btn flat icon color="red" title="Remover">
                    <v-icon>delete</v-icon>
                  </v-btn>
                  <v-btn flat icon color="indigo" title="Editar">
                    <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn flat icon color="black" title="Detalhar">
                    <v-icon>more_vert</v-icon>
                  </v-btn>
                </td>
              </template>
              <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                Sua busca por "{{ search }}" não obteve resultados.
              </v-alert>
            </v-data-table>
            <div class="text-xs-right">
              <v-btn dark color="primary" to="/alunos/novo">
                <v-icon>add</v-icon> Novo Aluno
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
  name: 'BuscarAluno',
  data() {
    return {
      titulo: 'Buscar alunos',
      search: '',
        headers: [
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
        { text: 'Telefone', value: 'telefone' },
        { text: 'Ações' }
      ],
      alunos: [],
    };
  },
  mounted() {
    axios.get('http://localhost:3000/api/alunos')
      .then(response => {
        this.alunos = response.data;
      })
      .catch(error => console.log(error));
  },
};
</script>
