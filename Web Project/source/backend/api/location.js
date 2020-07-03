const request = require("request");
const config = require("./../config");

const coordinates = (address, getWeather) => {
  if (address) {
    const url = `${config["MAPBOX_API_URL"]}${address}.json?access_token=${config["MAPBOX_API_KEY"]}`;
    console.log(url);
    request.get({ url: url, json: true }, (err, res) => {
      if (err) {
        return getWeather("Please try after sometime", undefined);
      } else {
        if (res.body.message) {
          return getWeather(res.body.message, undefined);
        } else {
          if (res.body.features[0]) {
            const result = {
              longitude: res.body.features[0].center[0],
              latitude: res.body.features[0].center[1],
              place: res.body.features[0].place_name,
            };
            console.log(result);
            return getWeather(undefined, result);
          }
          return getWeather("Please enter proper location", undefined);
        }
      }
    });
  }
};

module.exports = {
  getCoordinates: coordinates,
};
