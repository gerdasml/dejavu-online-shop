import * as React from "react";
import ImageGallery from "react-image-gallery";

import "react-image-gallery/styles/css/image-gallery.css";

export class Carousel extends React.Component {
  render () {
    const images = [
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      },
      {
        original: "http://159.89.106.85/image/3",
        thumbnail: "http://159.89.106.85/image/3"
      }
    ];
    return (
      <ImageGallery items={images}
                    showFullscreenButton={false}
                    thumbnailPosition="left"
                    showPlayButton={false}
      />
    );
  }
}
