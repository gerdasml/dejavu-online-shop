var path = require("path");
const merge = require("webpack-merge");
const webpack = require("webpack");
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const CompressionPlugin = require("compression-webpack-plugin");
const tsImportPluginFactory = require("ts-import-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, "app"),
    output: path.join(__dirname, "../../../target/classes/static")
};

const common = {
    entry: {
        app: PATHS.source,
    },
    cache: true,
    mode: "development",
    output: {
        path: PATHS.output,
        publicPath: "",
        filename: "bundle.js"
    },
    plugins: [
        new webpack.DefinePlugin({ // <-- key to reducing React's size
            'process.env': {
                'NODE_ENV': JSON.stringify('production')
            }
        }),
        // new BundleAnalyzerPlugin(), // Enable this to view bundle decomposition
        new CompressionPlugin({
            asset: "[path].gz[query]",
            algorithm: "gzip",
            test: /\.js$|\.css$|\.html$/,
            threshold: 10240,
            minRatio: 0.8
        }),
        new MiniCssExtractPlugin({
            filename: "[name].css",
            chunkFilename: "[id].css"
        }),
    ],
    module: {
        rules: [
            {
                test: /\.svg$/,
                loader: "svg-inline-loader"
            },
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    "css-loader"
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
            {
                test: /\.(jsx|tsx|js|ts)$/,
                loader: 'ts-loader',
                options: {
                    transpileOnly: true,
                    getCustomTransformers: () => ({
                        before: [ tsImportPluginFactory({
                            libraryName: "antd",
                            style: "css",
                            libraryDirectory: "lib"
                        }) ]
                    }),
                    compilerOptions: {
                        module: 'es2015'
                    }
                },
                exclude: /node_modules/
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