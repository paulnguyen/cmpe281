# Auth0 + Go Web App Sample

## Running the App

To run the app, make sure you have **go** and **goget** installed.

Rename the `.env.example` file to `.env` and provide your Auth0 credentials.

```bash
# .env

AUTH0_CLIENT_ID=AmMlIqVVZdFaC73w3XSK5rZw7475mPsT
AUTH0_DOMAIN=pnguyen.auth0.com
AUTH0_CLIENT_SECRET=-j-TckAEUmuN-yXIA2V2X8HFugkxV6-qGEHs0Zf_S6_xRqgADx7jLMHlonXcOKDv
AUTH0_CALLBACK_URL=http://localhost:3000/callback
```

Once you've set your Auth0 credentials in the `.env` file, run `go get .` to install the Go dependencies.

Run `go run main.go server.go` to start the app and navigate to [http://localhost:3000/](http://localhost:3000/)
