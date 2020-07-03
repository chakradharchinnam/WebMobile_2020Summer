const mongoose = require("mongoose");

const weatherSchema = new mongoose.Schema({
  summary: String,
  location: String,
  temperature: String,
  humidity: String,
  pressure: String,
  rainChance: String,
  windSpeed: String,
});

const weather = mongoose.model("Weather", weatherSchema);

module.exports = weather;
