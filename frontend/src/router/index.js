import Vue from 'vue';
import Router from 'vue-router';
import BuscarAluno from '@/components/BuscarAluno';
import NovoAluno from '@/components/NovoAluno';
import BuscarCurso from '@/components/BuscarCurso';
import NovoCurso from '@/components/NovoCurso';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/alunos',
      name: 'BuscarAluno',
      component: BuscarAluno,
    },
    {
      path: '/alunos/novo',
      name: 'NovoAluno',
      component: NovoAluno,
    },
    {
      path: '/cursos',
      name: 'BuscarCurso',
      component: BuscarCurso,
    },
    {
      path: '/cursos/novo',
      name: 'NovoCurso',
      component: NovoCurso,
    },
  ],
});
