import { WeatherService } from "./weather.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-weather-component",
  templateUrl: "./weather-component.component.html",
  styleUrls: ["./weather-component.component.css"],
})
export class WeatherComponentComponent implements OnInit {
  public showError = false;
  public showInvalidError = false;
  public weatherInfo: any;
  public locationName: String;
  public recentSearches: any = [];
  constructor(private weatherService: WeatherService) {}

  ngOnInit() {
    this.getRecentSearches();
  }

  getRecentSearches() {
    this.weatherService.getRecentData().subscribe((data) => {
      this.recentSearches = data;
    });
  }

  getWeatherData() {
    this.weatherService.getWeatherInfo(this.locationName).subscribe((data) => {
      this.weatherInfo = {
        location: data["location"],
        ...data["summary"],
      };
      this.getRecentSearches();
    });
  }
}
