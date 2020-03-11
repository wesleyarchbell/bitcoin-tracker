import React, { Component } from "react";
import BitcoinLogo from '../../components/BitcoinLogo/BitcoinLogo';
import BitcoinChart from '../../components/BitcoinChart/BitcoinChart';
import { TabContent, TabPane, Nav, NavItem, NavLink, Container, Row, Col, Badge, Spinner } from 'reactstrap';
import { connect } from 'react-redux';
import classnames from 'classnames';
import styles from './BitcoinPriceTracker.module.css';
import * as actions from '../../store/actions/index';

class BitcoinPriceTracker extends Component {

    componentDidMount() {       
        document.title = "Bitcoin Price Tracker";                  
        setInterval(() => {            
            this.props.fetchLatestPrice();            
            this.forceUpdate();
        }, 5000);
        this.props.fetchHistoricalPrices();    
    }
    
    render() {    

        const spinner = <Spinner className={styles.spinner} color="dark" />;
        let currentPriceChart = spinner;
        if (this.props.currentData.length > 0) {
            currentPriceChart = <BitcoinChart label="BTC Price - Current" data={this.props.currentData} color="#3E517A" />;
        }

        let historicalPriceChart = spinner;
        if (this.props.historicalData.length > 0) {
            historicalPriceChart = <BitcoinChart label="BTC Price - Last 30 Days" data={this.props.historicalData} color="#3E517A" />;
        }

        return (            
            <Container>
                <Row>
                    <Col sm="5"><BitcoinLogo/></Col>
                    <Col sm="5"><h1><Badge>Bitcoin Price Tracker</Badge></h1></Col>
                    <Col sm="5">Powered by <a href="https://www.coindesk.com/price/bitcoin">CoinDesk</a></Col>
                </Row>
                <Nav tabs>
                    <NavItem>
                        <NavLink href="#" className={classnames({active: this.props.activeTab === '1'})}
                            onClick={() => this.props.setActiveTab('1')}>
                            Current Price
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="#" className={classnames({active: this.props.activeTab === '2'})}
                            onClick={() => this.props.setActiveTab('2')}>
                            Historical Prices
                        </NavLink>
                    </NavItem>
                </Nav>  
                <TabContent activeTab={this.props.activeTab}>
                    <TabPane tabId="1">
                        <Row >
                            <Col>{currentPriceChart}</Col>
                        </Row>
                    </TabPane>
                    <TabPane tabId="2">
                        <Row >
                            <Col>{historicalPriceChart}</Col>
                        </Row>
                    </TabPane>
                </TabContent>                                          
            </Container>
        )
    }
}

const mapStateToProps = state => {
    return {
        currentData: state.currentData,
        historicalData: state.historicalData,
        activeTab: state.activeTab,
        fetchDataFailed: state.fetchDataFailed   
    };
}

const mapDispatchToProps = dispatch => {
    return {
        fetchLatestPrice: () => dispatch(actions.fetchCurrentData()),
        fetchHistoricalPrices: () => dispatch(actions.fetchHistoricalData()),
        setActiveTab: (tab) => dispatch(actions.setActiveTab(tab))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(BitcoinPriceTracker);