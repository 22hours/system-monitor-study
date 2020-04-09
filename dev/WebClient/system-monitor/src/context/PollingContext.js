import { createContext } from 'react';

const PollingContext = 
createContext({ isPolling: Boolean, setIsPolling: () => {} });

export default PollingContext;