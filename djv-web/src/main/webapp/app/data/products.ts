import { IProduct } from "../model/Product";

export const products: IProduct[] = [
    {
        name: "Teleponas",
        description: "Didelis teleponas",
        imageUrl: "http://pluspng.com/img-png/png-mobile-phone-huawei-nova-plus-378.png",
        price: 6969.69
    },
    {
        name: "Keptuve",
        description: "Galima kept viska su situ daiktu",
        imageUrl: "http://pngimg.com/uploads/frying_pan/frying_pan_PNG8358.png",
        price: 419.99
    },
    {
        name: "Siurblis",
        description: "It really sucks :)",
        imageUrl: "http://www.pngpix.com/wp-content/uploads/2016/04/Dirt-Vacuum-Cleaner-PNG-image-500x852.png",
        price: 1234
    },
    {
        name: "Lovike",
        description: "Galima prigult",
        imageUrl: "https://orig00.deviantart.net/e069/f/2017/018/6/e/bed_png_by_makiskan-davw0ul.png",
        price: 9999
    },
    {
        name: "Masiniukas",
        description: "Vroom vroom",
        imageUrl: "https://vignette.wikia.nocookie.net/pixar/images/b/b2/Lightning_mcqueen_cars_2.png/revision/20160305115405",
        price: 5000000
    },
    {
        name: "Diplomas",
        description: "Padeda gaut darba",
        imageUrl: "http://www.urbanbliss.com/wp-content/uploads/2016/09/Urban-Bliss-Diploma.png",
        price: 5.19
    },
    {
        name: "Knopke",
        description: "Paspaust galima",
        imageUrl: "https://upload.wikimedia.org/wikipedia/commons/5/5a/Perspective-Button-Stop-icon.png",
        price: 15.2
    },
    {
        name: "Bandele su bulviu kose",
        description: "Labai skanu ir sveika",
        imageUrl: "data:image/jpeg;base64,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",
        price: 12.3
    }
];
