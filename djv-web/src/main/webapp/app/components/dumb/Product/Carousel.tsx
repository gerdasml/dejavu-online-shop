import * as React from "react";
import Slider from "react-slick";

import "../../../../style/carousel.css";

export class Carousel extends React.Component {
  render () {
    const settings = {
    customPaging: (i: number) =>
      (
        <a>
          <img src={`https://s3.amazonaws.com/static.neostack.com/img/react-slick/abstract0${i + 1}.jpg`} />
        </a>
      ),
      dots: true,
      dotsClass: "slick-dots slick-thumb",
      infinite: true,
      slidesToScroll: 1,
      slidesToShow: 1,
      speed: 500
    };
    return (
      <div>
        <h2>Custom Paging</h2>
        <Slider {...settings}>
          <div><img src="https://s3.amazonaws.com/static.neostack.com/img/react-slick/abstract01.jpg" /></div>
          <div><img src="https://s3.amazonaws.com/static.neostack.com/img/react-slick/abstract02.jpg" /></div>
          <div><img src="https://s3.amazonaws.com/static.neostack.com/img/react-slick/abstract03.jpg" /></div>
          <div><img src="https://s3.amazonaws.com/static.neostack.com/img/react-slick/abstract04.jpg" /></div>
        </Slider>
      </div>
    );
  }
}
