import * as actionTypes from './actionTypes';
import axios from '../../axios/axiosBase';

const setChartData = (chartType, data) => {
    return {
        type: actionTypes.SET_CHART_DATA,
        chartType: chartType,
        data: data
    }
}

const fetchDataFailed = () => {
    return {
        type: actionTypes.FETCH_DATA_FAILED
    }
}

export const setActiveTab = (activeTab) => {
    return {
        type: actionTypes.SET_ACTIVE_TAB,
        tab: activeTab
    }
}

export const fetchCurrentData = () => {
    return fetchData("price/latest", actionTypes.CURRENT_CHART);
}

export const fetchHistoricalData = () => {
    return fetchData("price/historical", actionTypes.HISTORICAL_CHART);
}

const fetchData = (url, charType) => {
    return dispatch => {
        axios.get(url, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            }
        }).then(response => {
            dispatch(setChartData(charType, response.data));    
        }).catch(error => {
            dispatch(fetchDataFailed());
        });
    }
}






