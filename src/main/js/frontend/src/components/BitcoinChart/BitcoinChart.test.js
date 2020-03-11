import React from 'react';
import Enzyme, { shallow, mount } from 'enzyme';
import BitcoinChart from './BitcoinChart';

describe("BitcoinChart", () => {

    const dataMap = [
        {
            price: 3435.22,
            time: "2012-01-1 01:01:01"
        }
    ];

    it("Render chart", () => {
        const chart = shallow(<BitcoinChart data={dataMap} label="Test Chart"/>);            
        expect(chart).toMatchSnapshot();
    });
});