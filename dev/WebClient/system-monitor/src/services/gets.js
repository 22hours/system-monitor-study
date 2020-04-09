import axios from 'axios';

export const getAllPcs = () => {
    return axios.get('http://13.125.208.19/mobile/pc');
}