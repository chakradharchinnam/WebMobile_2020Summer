import { WeatherComponentComponent } from "./weather/weather-component.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

const routes: Routes = [
  {
    path: "weather",
    component: WeatherComponentComponent,
  },
  {
    path: "",
    redirectTo: "weather",
    pathMatch: "full",
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
