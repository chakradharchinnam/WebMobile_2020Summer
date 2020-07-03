const request = require("request");
const config = require("./../config");

const weather = (long, lat, callback) => {
  const apiUrl = `https://api.darksky.net/forecast/${config["DARK_SKY_KEY"]}/${lat},${long}`;
  request.get({ url: apiUrl, json: true }, (err, res) => {
    if (err) {
      callback("Please try after sometime", undefined);
    } else {
      if (res.body.error) {
        callback(res.body.error, undefined);
      } else {
        const result = {
          summary: res.body.currently["summary"],
          temperature: res.body.currently["temperature"],
          humidity: res.body.currently["humidity"],
          pressure: res.body.currently["pressure"],
          rainChance: res.body.currently["precipProbability"],
          windSpeed: res.body.currently["windSpeed"],
        };
        callback(undefined, result);
      }
    }
  });
};

module.exports = {
  weather: weather,
};
