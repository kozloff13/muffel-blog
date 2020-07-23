import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Params, ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit, OnDestroy {
  form: FormGroup;
  aSub: Subscription;
  alert = '';
  isLoading = false;
  constructor(
    private auth: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.form = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(6)
      ])
    });

    this.route.queryParams.subscribe((params: Params) => {
      if (params.accessDenied) {
        this.alert = 'В доступе отказано!';
      } else if (params.logout) {
        this.alert = 'Вы вышли из учетной записи! Авторизуйтесь!';
      }
    });
  }

  onSubmit() {
    this.form.disable();
    this.isLoading = true;
    setTimeout(() => {
      this.aSub = this.auth.login(this.form.value).subscribe(
        () => {
          this.isLoading = false;
          this.router.navigate(['/admin']);
        },
        ({ error: { message } }) => {
          this.isLoading = false;
          this.alert = message;
          this.form.enable();
        }
      );
    }, 1000);
  }

  ngOnDestroy() {
    this.isLoading = false;
    this.alert = '';
    if (this.aSub) {
      this.aSub.unsubscribe();
    }
  }
}
