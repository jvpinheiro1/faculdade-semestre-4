import { useState, useEffect } from 'react'; // Foi adicionado os hooks
import { View, Text, Button, FlatList, StyleSheet, PermissionsAndroid, Platform } from 'react-native';
import { BleManager } from 'react-native-ble-plx';

const manager = new BleManager();

export default function bleScannerComponent() {

  // Variavel de estado 'devices' para guardar lista de dispositivos
  const [devices, setDevices] = useState([]);

  // O estado 'radioPowerOn' verifica se o bluetooth está ligado (true) ou desligado (false)
  const [radioPowerOn, setRadioPowerOn] = useState(false);

  useEffect(() => {
    const subscription = manager.onStateChange((state) => {
      if (state === "PoweredOn") {
        setRadioPowerOn(true);
        subscription.remove();
      }
    }, true);
    return () => {
      subscription.remove();
      manager.destroy();
    }
  }, [])

  const requestBluetoothPermission = async () => {

    if (Platform.OS === 'android') {
      // Busca saber qual API -  A partir do android 12 (API31), as permissões explicitas do
      // bluetooth são necessárias
      const apilevel = parseInt(Platform.Version.toString(), 10);
      if (apilevel < 31) {
        // Para android < 12, a permissão de localização é suficiente.
        const granted = await PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          {
            title: 'Permissão de localização',
            message: 'O app precisa de acesso á sua localização para scannear dispositivos bluetooth',
            buttonPositive: 'OK'
          },
        );
        return granted === PermissionsAndroid.RESULTS.GRANTED;
      } else {
        // Caso Android é versão 12 ou mais, deve ser solicitada as novas permissões de Bluetooth
        const result = await PermissionsAndroid.requestMultiple(
          [
            PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN,
            PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION // Permissão localização ainda necessária
          ]
        );

        return (
          result[PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN] === PermissionsAndroid.RESULTS.GRANTED &&
          result[PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT] === PermissionsAndroid.RESULTS.GRANTED &&
          result[PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION] === PermissionsAndroid.RESULTS.GRANTED
        );
      }
    }
    return true;
  }

  const scanForDevices = async () => {
    const hasPermission = await requestBluetoothPermission();
    if(!hasPermission){
      alert('Permissão negada. O aplicativo não pode escanear dispositivos bluetooth!');
      return;
    }
    if(!radioPowerOn){
      alert('Por favor, ligue o Bluetooth para escanear dispositivos.')
      return;
    }
    setDevices([]);
    manager.startDeviceScan(null, null, (error, device) => {
      if(error){
        console.log('ERROR SCAN: ', error)
        if(error.errorCode === 601){
          alert('Erro permissão de SCAN. Verifique a permissão de dispositivos proximo')
        }
        manager.stopDeviceScan();
        return;
      }
      if (device && device.name){
        setDevices(prevDevices => {
          if(!prevDevices.some(d => d.id === device.id)){
            return [...prevDevices, device];
          }
          return prevDevices;
        })
      }
    });

    setTimeout(()=>{
      manager.stopDeviceScan(), 5000
    })
  }

  return(
    <View>
      <Text>
        
      </Text>
    </View>
  )

}

