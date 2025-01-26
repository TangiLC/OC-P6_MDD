import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule,
  ],
})
export class CreateCommentComponent {
  @Input() articleId!: number;
  commentForm: FormGroup;

  constructor(private fb: FormBuilder, private snackBar: MatSnackBar) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  onSubmit(): void {
    if (this.commentForm.valid) {
      const commentData = {
        articleId: this.articleId,
        content: this.commentForm.value.content,
      };

      console.log('Submitting comment:', commentData);

      setTimeout(() => {
        this.snackBar.open('Commentaire créé avec succès !', '', {
          duration: 2000,
        });
        this.commentForm.reset();
      }, 500);
    }
  }
}
