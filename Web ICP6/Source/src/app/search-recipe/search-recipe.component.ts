import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from './../../environments/environment';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  private FOODAPIID= environment.DAM_APP_ID;
  private FOODAPIKEY = environment.DAM_APP_KEY;
  private VENN_ID = environment.VENNIE_CLIENT_ID;
  private VENN_KEY = environment.VENNIE_CLIENT_SECRET;
  private recipe_url = `https://api.edamam.com/search?app_id=${this.FOODAPIID}&app_key=${this.FOODAPIKEY}`;
  private restaurant_url = `https://api.foursquare.com/v2/venues/search?client_id=${this.VENN_ID}&client_secret=${this.VENN_KEY}&v=20180323`;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      this.getRecipeData();
    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this.getPlacesData();
    }
  }

  getRecipeData() {
    this.recipeList = [];
    var url = this.recipe_url + "&q=" + this.recipeValue;
    this._http.get(url).subscribe(data => {
      let results = data["hits"];
      results.forEach(result => {
        this.recipeList.push({
          name: result["recipe"].labels,
          url : result["recipe"].url,
          icon: result["recipe"].image
        })
      })
    })
  }

  getPlacesData() {
    this.venueList =[];
    var apiurl = this.restaurant_url + "&near=" + this.placeValue + "&query=" + this.recipeValue;
    this._http.get(apiurl).subscribe(data => {
      let restaurants = data["response"]["venues"];
      restaurants.forEach(restaurant => {
        this.venueList.push({
          name : restaurant["name"],
          location : {
            formattedAddress: [restaurant["location"]["address"], restaurant["location"]["city"], restaurant["location"]["country"]]
          }
        })
      })
    })
  }
}
