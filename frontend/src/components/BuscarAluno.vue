<template>
  <div>
    <v-container grid-list-md text-xs-left>
      <v-layout row wrap>
        <v-flex xs12>
          <v-flex class="headline font-weight-bold">
            Lista de alunos
            <v-spacer/>
            <v-text-field
              v-model="search"
              append-icon="search"
              label="Buscar"
              single-line
              hide-details
            ></v-text-field>
          </v-flex>
          <v-data-table
            :headers="headers"
            :items="alunos"
            :search="search"
            ref="imprimir"
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
                <v-btn flat icon color="red" title="Remover"
                  @click="deleteAluno(props.item)"
                >
                  <v-icon>delete</v-icon>
                </v-btn>
                <v-btn
                  flat icon color="indigo"
                  title="Editar"
                  :to="{ name: 'NovoAluno', params: { edit: true, id_aluno: props.item.id }}"
                >
                  <v-icon>edit</v-icon>
                </v-btn>
              </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
              Sua busca por "{{ search }}" não obteve resultados.
            </v-alert>
          </v-data-table>
          <v-flex class="text-xs-right">
            <v-btn
              dark color="primary"
              @click="downloadCsv({'filename': 'result.csv'})">
              <v-icon>get_app</v-icon> Download CSV
            </v-btn>
            <v-btn dark color="primary" to="/alunos/novo">
              <v-icon>add</v-icon> Novo Aluno
            </v-btn>
          </v-flex>
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
        { text: 'Ações', value: '' }
      ],
      alunos: [],
    };
  },
  methods: {
    deleteAluno(obj) {
      axios.delete('http://localhost:3000/api/alunos/'+obj.id)
        .then(response => {
          this.alunos.splice(this.alunos.indexOf(obj), 1);
        })
        .catch(error => {
          console.log(error.response);
        });
    },
    imprimirAlunos() {
      let w = window.open("");
      w.document.write(this.alunos);
      w.print();
      w.close();
    },
    toCsv(args) {
      var result, ctr, keys, columnDelimiter, lineDelimiter, data;

      data = args.data || null;
      if (data == null || !data.length) {
        return null;
      }

      columnDelimiter = args.columnDelimiter || ';';
      lineDelimiter = args.lineDelimiter || '\n';

      keys = Object.keys(data[0]);

      result = '';
      result += keys.join(columnDelimiter);
      result += lineDelimiter;

      data.forEach(function(item) {
        ctr = 0;
        keys.forEach(function(key) {
          if (ctr > 0) result += columnDelimiter;

          result += item[key];
          ctr++;
        });
        result += lineDelimiter;
      });

      return result;
    },
    downloadCsv(args) {
      var data, filename, link;
      var csv = this.toCsv({
        data: this.alunos
      });
      if (csv == null) return;

      filename = args.filename || 'export.csv';

      if (!csv.match(/^data:text\/csv/i)) {
          csv = 'data:text/csv;charset=utf-8,' + csv;
      }
      data = encodeURI(csv);
      
      link = document.createElement('a');
      link.setAttribute('href', data);
      link.setAttribute('download', filename);
      document.body.appendChild(link);
      link.click();
    }
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
