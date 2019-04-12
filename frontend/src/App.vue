<template>
  <div id="app">
    <v-toolbar>
      <v-toolbar-title>Desafio CEUMA</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn flat>Alunos</v-btn>
        <v-btn flat>Cursos</v-btn>
        <v-btn flat>Sobre</v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>
