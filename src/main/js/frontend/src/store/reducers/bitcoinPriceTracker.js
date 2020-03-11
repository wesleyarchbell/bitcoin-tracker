import * as actions from '../actions/actionTypes';

const initialState = {
    currentData: [],
    historicalData: [],
    activeTab: "1",
    fetchDataFailed: false,
    index: 0
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case (actions.SET_CHART_DATA): {
            if (actions.CURRENT_CHART === action.chartType) {
                let data = state.currentData;
                const found = state.currentData.filter(i => {
                    return i.time === action.data.lastUpdated;
                });
                if (found.length === 0) {
                    data.push({
                        time: action.data.lastUpdated,
                        price: action.data.price
                    });
                    if (data.length > 30) {
                        data = data.splice(1, data.length);
                    }                    
                }
                return {
                    ...state,
                    currentData: data,
                    fetchDataFailed: false,
                    index: state.index + 1
                }
            } else if (actions.HISTORICAL_CHART === action.chartType) {
                var data = state.historicalData;
                action.data.forEach(i => {
                    data.push({
                        time: i.lastUpdated,
                        price: i.price
                    });
                });
                return {
                    ...state,
                    historicalData: data,
                    fetchDataFailed: false
                }                
            }
            break;
        }   
        case (actions.FETCH_DATA_FAILED): {
            return {
                ...state,
                fetchDataFailed: true
            }
        }   
        case (actions.SET_ACTIVE_TAB): {
            if (state.activeTab !== action.tab) {
                return {
                    ...state,
                    activeTab: action.tab
                };
            }
            return state;
        }
        default: {
            return state;
        }
    }
}

export default reducer;