import React, { Component } from 'react';
import { Line } from 'react-chartjs-2'; 

class BitcoinChart extends Component {

    constructor(props) {
        super(props);
        this.state = {
            chartData: {}
        }
    }

    componentDidUpdate() {        
        if (this.chartReference) {
            this.chartReference.props.data.labels = this.props.data.map(d => d.time);
            this.chartReference.props.data.datasets[0].data = this.props.data.map(d => d.price);
        }
    }

    componentDidMount() {
        this.setState({
            chartData: {
                labels: this.props.data.map(d => d.time),
                datasets: [{
                    label: this.props.label,
                    data: this.props.data.map(d => d.price),
                    fill: 'none',
                    backgroundColor: this.props.color,
                    pointRadius: 1,
                    borderColor: this.props.color,
                    borderWidth: 5,
                    lineTension: 0
                }]
            }
        });
    }

    render() {
        return (
            <Line ref={(reference) => this.chartReference = reference } data={this.state.chartData}/>
        );
    }
};

export default BitcoinChart;