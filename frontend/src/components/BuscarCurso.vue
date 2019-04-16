<template>
  <div>
    <v-container grid-list-md text-xs-left>
      <v-layout row wrap>
        <v-flex xs12>          
          <v-flex class="headline font-weight-bold">
            Lista de cursos
            <v-spacer/>
            <v-text-field
              v-model="search"
              append-icon="search"
              label="Buscar por id, nome, data e carga horária"
              single-line
              hide-details
            ></v-text-field>
          </v-flex>
          <v-data-table
            :headers="headers"
            :items="cursos"
            :search="search"
          >
            <template v-slot:items="props">
              <td>{{ props.item.codigo }}</td>
              <td class="text-xs-left">{{ props.item.nome }}</td>
              <td class="text-xs-left">{{ props.item.dataCadastro }}</td>
              <td class="text-xs-left">{{ props.item.cargaHoraria }}</td>
              <td class="text-xs-left">
                <v-btn flat icon color="red" title="Remover"
                  @click="deleteCurso(props.item)"
                >
                  <v-icon>delete</v-icon>
                </v-btn>
                <v-btn 
                  flat icon color="indigo" 
                  title="Editar"
                  :to="{ name: 'NovoCurso', params: { edit: true, id_curso: props.item.id }}"
                >
                  <v-icon>edit</v-icon>
                </v-btn>
                <v-btn
                  flat icon color="black"
                  title="Alunos do curso"
                  @click="showDialog(props.item)"
                >
                  <v-icon>people</v-icon>
                </v-btn>
              </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
              Sua busca por "{{ search }}" não obteve resultados.
            </v-alert>
          </v-data-table>
          <div class="text-xs-right">
            <v-btn
              dark color="primary"
              @click="downloadCsv({'filename': 'result.csv'})">
              <v-icon>get_app</v-icon> Download CSV
            </v-btn>
            <v-btn dark color="primary" to="/cursos/novo">
              <v-icon>add</v-icon> Novo Curso
            </v-btn>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
    <v-dialog v-model="cursoAlunosDialog" persistent width="100%">
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
            :items="cursoSelecionado"
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
            @click="cursoAlunosDialog = false"
          >
            Fechar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
      cursoAlunosDialog: false,
      cursoSelecionado: []
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
    },
    showDialog(obj) {
      this.cursoAlunosDialog = true;
      this.cursoSelecionado = obj.alunos;
    },
    toCsv(args) {
      var result, ctr, keys, columnDelimiter, lineDelimiter, data;

      data = args.data || null;
      if (data == null || !data.length) {
        return null;
      }

      columnDelimiter = args.columnDelimiter || ',';
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
        data: this.cursos
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
    this.getCursos();
  },
};
</script>
