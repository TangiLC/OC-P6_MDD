import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticlesComponent } from './components/list/articles.component';
import { ArticleComponent } from './components/detail/article.component';
import { CreateArticleComponent } from './components/create/create-article.component';

const routes: Routes = [
  { path: '', component: ArticlesComponent },
  { path: 'create', component: CreateArticleComponent },
  { path: ':id', component: ArticleComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ArticlesRoutingModule {}
