import * as React from "react";
import ImageGallery from "react-image-gallery";

import "react-image-gallery/styles/css/image-gallery.css";

export interface CarouselProps {
  mainPicture: string;
  additionalPictures: string[];
}

export class Carousel extends React.Component<CarouselProps, never> {
  render () {
    const images = [this.props.mainPicture, ...this.props.additionalPictures]
                   .map(img => ({original: img, thumbnail: img}));
    return (
      <ImageGallery items={images}
                    showFullscreenButton={false}
                    thumbnailPosition="left"
                    showPlayButton={false}
      />
    );
  }
}
