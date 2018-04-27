import * as React from "react";

import "../../../../style/cart.css";

//this is bullshit. if you want to kill yourself, do it

export interface DateState { date: Date; }

export class Calendar extends React.Component<{}, DateState> {
  /*state = {
    date: new Date(),
  };*/

  /*onChange = (date: Date) => this.setState({ date });*/

  render () {
    return (
        /*<DatePicker className="datePicker"
          onChange={this.onChange}
          value={this.state.date}
    />*/

    {/*<SingleDatePicker
  date={this.state.date} // momentPropTypes.momentObj or null
  onDateChange={date => this.setState({ date })} // PropTypes.func.isRequired
  focused={this.state.focused} // PropTypes.bool
  onFocusChange={({ focused }) => this.setState({ focused })} // PropTypes.func.isRequired
/>
    );*/});
  }
}
