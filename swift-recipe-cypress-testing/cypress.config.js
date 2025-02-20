const { defineConfig } = require("cypress");
const preprocessor = require("@badeball/cypress-cucumber-preprocessor");
const browserify = require("@badeball/cypress-cucumber-preprocessor/browserify");

async function setupNodeEvents(cypressOn, config) {
  const on = require("cypress-on-fix")(cypressOn)
  await preprocessor.addCucumberPreprocessorPlugin(on, config);
  on("file:preprocessor", browserify.default(config));
  return config
}

module.exports = defineConfig({
  defaultCommandTimeout: 6000,
  env: {
    url: process.env.CYPRESS_URL || "http://localhost:8080/login"
  },
  retries: {
    runMode: 1
  },
  e2e: {
    setupNodeEvents,
    baseUrl: process.env.CYPRESS_URL || "http://localhost:8080/login",
    viewportWidth: 2560,
    viewportHeight: 1440,
    specPattern: "cypress/integration/features/*.feature",
    reporter: "mochawesome",
    reporterOptions: {
      reportDir: "cypress/reports",
      overwrite: true,
      html: true,
      json: true,
      timestamp: "mmddyyyy_HHMMss"
    },
    video: true,
    videoCompression: 32,
    videosFolder: "cypress/videos"
  },
});