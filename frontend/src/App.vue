<template>
  <div id="app">
    <v-app>
      <v-toolbar dark color="primary">
        <v-toolbar-title>Desafio CEUMA</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items class="hidden-sm-and-down">
          <v-btn flat>Alunos</v-btn>
          <v-btn flat>Cursos</v-btn>
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
