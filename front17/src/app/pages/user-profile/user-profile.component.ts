import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  ValidatorFn,
  AbstractControl,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { UserInformation } from '../../interfaces/userInformation.interface';
import { UpdateUserDto } from '../../interfaces/updateUserDto.interface';

export function notBlankValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    const isValid =
      value !== null && value !== undefined && value.trim() !== '';
    return isValid ? null : { blank: { value: value } };
  };
}

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss',
})
export class UserProfile implements OnInit {
  userInfo: UserInformation | null = null;
  profileForm: FormGroup;
  hidePassword = true;

  constructor(
    private userService: UserService,
    public authService: AuthService,
    private fb: FormBuilder
  ) {
    this.profileForm = this.fb.group({
      username: [
        '',
        [
          Validators.required,
          notBlankValidator(),
          Validators.minLength(3),
          Validators.maxLength(30),
        ],
      ],
      password: [
        '',
        [
          Validators.minLength(6),
          Validators.maxLength(40),
          (control: AbstractControl) => (control.value ? null : null),
        ],
      ],
    });
  }

  ngOnInit(): void {
    this.authService.userInfo$.subscribe((info) => {
      this.userInfo = info;
      if (info) {
        this.profileForm.patchValue({
          username: info.username,
        });
      }
    });
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(): void {
    if (this.profileForm.valid && this.userInfo) {
      const formValues = this.profileForm.value;
      const updateDto: UpdateUserDto = {
        username:
          formValues.username !== this.userInfo.username
            ? formValues.username
            : null,
        password: formValues.password || null,
      };

      this.userService.updateUser(this.userInfo.id, updateDto).subscribe({
        next: () => {
          console.log('Profile updated successfully');
          // TODO: Add toast/snackbar notification
        },
        error: (err) => {
          console.error('Profile update failed', err);
          // TODO: Add error handling toast/snackbar
        },
      });
    }
  }
}
