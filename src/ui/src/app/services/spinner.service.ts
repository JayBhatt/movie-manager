import { Injectable } from '@angular/core';
import { NgxSpinnerService } from "ngx-spinner";

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  private readonly spinner: NgxSpinnerService; 
  private static readonly TIMEOUT: number = 6000;
  
  constructor(spinner: NgxSpinnerService) {
    this.spinner = spinner;  
  }

  public show() {
    this.spinner.show();
    setTimeout(() => {
      this.spinner.hide();
    }, SpinnerService.TIMEOUT);
  }

  public hide() {
    this.spinner.hide();
  }

}
