import * as React from "react";

import { Grid, Header, GridColumn} from "semantic-ui-react";

import {RouteComponentProps} from "react-router-dom";
import { Carousel } from "../dumb/Product/Carousel";
import { PropertiesTable } from "../smart/Product/PropertiesTable";

import "../../../style/product.css";

interface IProductRouteProps { id: number; }
const product = {
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec aliquam porta consequat. Nullam suscipit, nisi eu aliquam euismod, dui diam sagittis eros, ut fermentum diam lectus ac risus. Integer ornare arcu nisl, eget condimentum sapien mattis in. Duis hendrerit diam id facilisis rutrum. Vivamus quam lacus, sollicitudin a sagittis ac, feugiat tincidunt tellus. Aenean consectetur, mi vel ultrices bibendum, neque ex volutpat purus, quis posuere diam mauris id libero. Fusce a sem eget nulla porta vulputate. Etiam rutrum hendrerit lorem et tempus. Nullam malesuada dui nunc, quis convallis ex euismod non. Nulla mi neque, volutpat lobortis scelerisque vitae, ornare nec nisl. Etiam maximus tortor id nisl ullamcorper condimentum. In hac habitasse platea dictumst.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec aliquet venenatis velit gravida pharetra. Curabitur dictum efficitur eros, sed sagittis ex mattis sit amet. Fusce at urna sit amet leo rhoncus imperdiet vitae sit amet libero. Duis ultrices velit ac tortor pulvinar, ac lobortis risus efficitur. Donec interdum elementum lectus. Pellentesque pretium iaculis quam. Aliquam in dolor sit amet mi vestibulum volutpat. Pellentesque tincidunt nulla sed lorem condimentum, at auctor urna auctor. Cras non lobortis nisi. Nunc imperdiet non arcu quis aliquet. Donec nisl erat, facilisis quis egestas sed, fermentum ac turpis. Cras enim erat, sagittis eget porttitor in, dignissim et justo. Curabitur ac neque faucibus, elementum augue a, dignissim dolor. Aliquam lorem lectus, commodo sed sodales eget, efficitur at risus. Donec tempus sapien id justo faucibus, in consectetur mi iaculis. Aliquam et sagittis ante. Praesent nec nunc lobortis, vestibulum lacus sit amet, lobortis metus. Morbi massa velit, rutrum a venenatis eu, fermentum quis quam. Quisque vel consectetur eros. Ut lobortis augue a erat dapibus, quis congue nisl tempus. Donec ante odio, volutpat sed eros at, varius tincidunt erat. Nullam vitae nunc porta, luctus est quis, tincidunt tellus. Vivamus ut pulvinar sapien. Suspendisse fringilla justo sit amet lectus suscipit, nec tristique leo egestas. Vivamus rhoncus, mi fermentum pretium ultricies, lectus sapien pellentesque justo, at suscipit est nulla at nibh. Vestibulum ultricies porttitor tristique. Pellentesque lacus neque, mollis sed vehicula ut, malesuada vitae ex. Duis suscipit non turpis placerat tincidunt. Sed eu tristique elit, quis consectetur dui. Praesent sodales felis sit amet purus pretium sagittis. Donec ut mi vehicula, posuere libero at, rhoncus tellus. Mauris enim est, ornare vitae lorem at, efficitur dignissim odio. In ullamcorper efficitur faucibus. Etiam volutpat arcu nec magna ornare, eu porttitor turpis rhoncus. Etiam eleifend cursus ultrices. Morbi turpis justo, finibus et suscipit lobortis, auctor sed tellus. Nulla sit amet risus volutpat, fringilla dui non, volutpat metus. Donec eu lobortis mauris. Praesent non risus in lacus pharetra lacinia id vitae urna. Suspendisse non hendrerit nibh.",
    imageUrl: "///////",
    name: "Telefonas",
    price: 43,
    properties: [
       {
           name: "spalva",
           value: "raudona"
       },
       {
            name: "spalva",
            value: "raudona"
        },
        {
            name: "spalva",
            value: "raudona"
        },
        {
            name: "spalva",
            value: "raudona"
        },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       },
       {
           name: "dydis",
           value: "16 coliu"
       },
       {
           name: "svoris",
           value: "didelis"
       }
    ]
};
export const Product = (props: RouteComponentProps<IProductRouteProps>) => (
    <div className="product">
        <Header size="large">Product {props.match.params.id}</Header>
        <Grid>
            <Grid.Row>
                <Grid.Column width={8}>
                    <Carousel />
                    <Grid className="propertiesTable">
                        <PropertiesTable product={product}/>
                    </Grid>
                </Grid.Column>
                <Grid.Column width={1}/>
                <Grid.Column width={7} className="description">
                    {product.description}
                </Grid.Column>
            </Grid.Row>
        </Grid>
    </div>
);
