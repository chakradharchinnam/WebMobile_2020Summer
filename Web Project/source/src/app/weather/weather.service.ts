import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { $ } from "protractor";

@Injectable({
  providedIn: "root",
})
export class WeatherService {
  public API_URL = "http://localhost:5000/api/weather?location=";
  constructor(private http: HttpClient) {}

  getWeatherInfo(place) {
    return this.http.get(`${this.API_URL}${place}`);
  }

  getRecentData() {
    return this.http.get("http://localhost:5000/api/recent");
  }
}
