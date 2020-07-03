const express = require("express");
const path = require("path");
const coordinates = require("./api/location");
const weather = require("./api/weather");
const app = express();
const port = process.env.PORT || 5000;
const cors = require("cors");
const Weather = require("./models/weather");

const mongoose = require("mongoose");
mongoose
  .connect("mongodb://localhost:27017/webapp")
  .then(() => console.log("connection successful"))
  .catch((err) => console.error(err));

app.use(cors());

app.get("/api/recent", (req, res, next) => {
  Weather.find((err, reports) => {
    if (err) return next(err);
    res.json(reports);
  }).sort({ _id: -1 });
});

app.get("/api/weather", (req, res, next) => {
  const place = req.query.location;
  if (!place) {
    return res.send({
      error: "Please provide place to know weather information.",
    });
  }
  coordinates.getCoordinates(
    place,
    (err, { longitude, latitude, place } = "") => {
      if (err) {
        return res.send({ error: err });
      }
      weather.weather(longitude, latitude, (err, result) => {
        if (err) {
          return res.send({ error: err });
        }
        console.log(result);
        Weather.create({
          summary: result.summary,
          temperature: result.temperature,
          humidity: result["humidity"],
          pressure: result["pressure"],
          rainChance: result["precipProbability"],
          windSpeed: result["windSpeed"],
          location: place,
        })
          .then((data) => {
            console.log("Saved");
          })
          .catch((err) => {
            console.log(err);
          });
        res.send({
          summary: result,
          location: place,
        });
      });
    }
  );
});

app.get("*", (req, res) => {
  res.send("page-not-found");
});

app.listen(port, () => {
  console.log("Server is running on", port);
});
