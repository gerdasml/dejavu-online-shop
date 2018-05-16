var path = require("path");
const merge = require("webpack-merge");

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, "app"),
    output: path.join(__dirname, "../../../target/classes/static")
};

const common = {
    entry: [
        PATHS.source
    ],
    cache: true,
    mode: "development",
    output: {
        path: PATHS.output,
        publicPath: "",
        filename: "bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.svg$/,
                loader: "svg-inline-loader"
            },
            {
                test: /\.css$/,
                use: [
                    { loader: "style-loader" },
                    { loader: "css-loader" }
                ]
            },
            {
                test: /\.(woff2?|ttf|eot)/,
                use: {
                    loader: "url-loader",
                    options: {
                        limit: 50000
                    }
                }
            },
            {
                test: /\.(png|jpe?g|gif)$/,
                use: [
                    {
                        loader: "url-loader",
                        options: {
                            limit: 8000
                        }
                    }
                ]
            },
            // All files with a '.ts' or '.tsx' extension will be handled by 'awesome-typescript-loader'
            {
                test: /\.tsx?$/,
                loader: "ts-loader"
            },
            // All output '.js' files will have any sourcemaps re-processed by 'source-map-loader'
            {
                enforce: "pre",
                test: /\.js$/,
                exclude: /node_modules/,
                loader: "source-map-loader"
            }
        ]
    },
    resolve: {
        extensions: [".js", ".jsx", ".ts", ".tsx"]
    }
};

if (TARGET === "start" || !TARGET) {
    module.exports = merge(common, {
        devServer: {
            port: 9090,
            proxy: {
                "/": {
                    target: "http://localhost:8080",
                    secure: false,
                    prependPath: false
                }
            },
            publicPath: "http://localhost:9090/",
            historyApiFallback: true
        },
        devtool: "source-map"
    });
}

if (TARGET === "build") {
    module.exports = merge(common, {});
}