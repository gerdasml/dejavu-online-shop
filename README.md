# dejavu-online-shop

A project for the Software Development course in Vilnius University. 2018.

Ideas for the initial setup were taken from [here](https://github.com/dlizarra/spring-boot-react-webpack-starter).

### Getting started
1. Clone the repository
1. Perform Maven's `clean`-`install` routine. This might take some time. You can also use just `install` - it will take less time (but might leave some old code, so if you really want to be sure, `clean` first).
1. Start `OnlineShopApplication`. This will start the backend on `localhost:8080`. The current version of the frontend will also be visible here.
1. Go to `/src/main/webapp` and execute `npm start` from a terminal. This will start the frontend on `localhost:9090`. Live changes from the frontend will be visible here.

### Development
1. Frontend:
    1. If using Visual Studio Code for frontend development, you should install the [TSLint extension](https://marketplace.visualstudio.com/items?itemName=eg2.tslint). It will help conform to the TypeScript style guide.
    2. If you'd like to check, if the code really obeys the style rules, you can run `npm run lint`.
    3. Some linting errors can be fixed automatically. You can run `npm run lint:fix` and `tslint` will attempt to fix as many of the errors as possible.