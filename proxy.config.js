const { LogLevel } = require("@angular/compiler-cli");

const PROXY_CONFIG = [
    {
        context: ["/api"],
        target: "http://localhost:8093/",
        secure: false,
        LogLevel: "debug",
    },
];

module.exports = PROXY_CONFIG;