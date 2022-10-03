import { Component, OnInit } from '@angular/core';
import { Config } from 'src/app/models/config';
import { ConfigList } from 'src/app/models/config-list';
import { ConfigService } from 'src/app/services/config.service';
import { SpinnerService } from 'src/app/services/spinner.service';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.scss']
})
export class ConfigComponent implements OnInit {

  private readonly configSerice: ConfigService;
  private spinner: SpinnerService;
  public error: string = '';
  public configList: Array<Config> = new Array<Config>();

  constructor(configSerice: ConfigService, spinner: SpinnerService) {
    this.configSerice = configSerice;
    this.spinner = spinner;
    this.spinner.show();
  }

  ngOnInit(): void {
    this.loadConfig();
  }

  public loadConfig() {
    this.configSerice.fetch().subscribe({
      next: (list: any) => {
        this.configList = list;
        this.spinner.hide();
      },
      error: err => {
        this.error = err.error.message;
        this.spinner.hide();
      }
    });
  }

}
