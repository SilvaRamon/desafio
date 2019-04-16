<template>
  <div id="app">
    <v-app>
      <v-toolbar dark color="primary">
        <v-flex xs2 offset-x2>
         <v-toolbar-title>Desafio CEUMA</v-toolbar-title>
        </v-flex>
        <v-toolbar-items class="hidden-sm-and-down">
          <v-btn flat to="/alunos">Alunos</v-btn>
          <v-btn flat to="/cursos">Cursos</v-btn>
          <v-btn flat @click="dialog = true">Sobre</v-btn>
        </v-toolbar-items>
      </v-toolbar>
      <router-view/>
    </v-app>
    <v-dialog v-model="dialog" persistent max-width="400">
      <v-card>
        <v-card-title class="headline">Sobre o Desafio CEUMA</v-card-title>
        <v-card-text>
          <p>
            Esta aplicação tem o propósito ser aresentada como um 
            requisito para o seletivo da vaga de programador no CEUMA.
          </p>
          <p>Critérios realizados:</p>
          <ul>
            <li>CRUD alunos;</li>
            <li>Download de CSV com lista de alunos;</li>
            <li>CRUD cursos;</li>
            <li>Listar alunos em um curso;</li>
            <li>Download de CSV com lista de cursos;</li>
          </ul>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" flat @click="dialog = false">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      dialog: false,
    }
  },
};
</script>
