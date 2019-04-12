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
              :items="desserts"
              :search="search"
            >
              <template v-slot:items="props">
                <td>{{ props.item.codigo }}</td>
                <td class="text-xs-right">{{ props.item.nome }}</td>
                <td class="text-xs-right">{{ props.item.cpf }}</td>
                <td class="text-xs-right">{{ props.item.endereco }}</td>
                <td class="text-xs-right">{{ props.item.cep }}</td>
                <td class="text-xs-right">{{ props.item.email }}</td>
                <td class="text-xs-right">{{ props.item.telefone }}</td>
              </template>
              <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                Sua busca por "{{ search }}" não obteve resultados.
              </v-alert>
            </v-data-table>
            <div class="text-xs-right">
              <v-btn dark color="primary" to="/alunos/novo">
                Novo Aluno
              </v-btn>
            </div>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
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
        { text: 'Telefone', value: 'telefone' }
      ],
      desserts: [
        {
          codigo: '123',
          nome: 'Pessoa 1',
          cpf: '111.111.111-11',
          endereco: 'rua dos bobos, nº zero.',
          cep: '99999-999',
          email: 'pessoa1@mail.com',
          telefone: '(99) 99999-9999'
        },
        {
          codigo: '223',
          nome: 'Pessoa 2',
          cpf: '222.222.222-22',
          endereco: 'rua dos bobos, nº um.',
          cep: '88888-888',
          email: 'pessoa2@mail.com',
          telefone: '(88) 88888-8888'
        },
        {
          codigo: '323',
          nome: 'Pessoa 3',
          cpf: '333.333.333-33',
          endereco: 'rua dos bobos, nº dois.',
          cep: '77777-777',
          email: 'pessoa3@mail.com',
          telefone: '(77) 77777-7777'
        },
        {
          codigo: '423',
          nome: 'Pessoa 4',
          cpf: '444.444.444-44',
          endereco: 'rua dos bobos, nº três.',
          cep: '66666-666',
          email: 'pessoa4@mail.com',
          telefone: '(66) 66666-6666'
        },
      ]
    };
  },
};
</script>
