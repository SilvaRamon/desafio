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
          <v-btn flat>Sobre</v-btn>
        </v-toolbar-items>
      </v-toolbar>
      <router-view/>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>
