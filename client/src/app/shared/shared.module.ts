import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from './components/loader/loader.component';

const exported = [
  LoaderComponent
];

@NgModule({
  declarations: [...exported],
  exports: [...exported],
  imports: [CommonModule]
})
export class SharedModule {}
