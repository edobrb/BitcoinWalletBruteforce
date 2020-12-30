# Bitcoin Wallet Brute force

This script try to find a private key for one of the bitcoin wallet listed in `wallets.txt`.


The script only use one thread and on mine i7-7700k it checks ~ 2000 wallets/second.

The list of wallets inside `wallets.txt` are empty random generated wallets, so replace them by the addresses of your interest.
If you have lost a wallet and want try to retrieve it against the laws of nature you can try to put your wallet address inside the file and run the script.

## Download
 - [bruteforce-bitcoin-wallet.zip](https://github.com/edobrb/BruteforceBitcoinWallet/releases/download/1.0.0/bruteforce-bitcoin-wallet.zip)
 - [bruteforce-bitcoin-wallet.tar](https://github.com/edobrb/BruteforceBitcoinWallet/releases/download/1.0.0/bruteforce-bitcoin-wallet.tar)


## Requirements
 - Java 11 or later
 - ~ 7.46^60 years (using only one core for cracking a single wallet)
 
## Usage
`java -jar bruteforce-bitcoin-wallet.jar`



**If the script finds a valid key for one of the wallets listed in `wallets.txt` it will be printed inside `found.txt`**
![like that's ever gonna happen](shrek.jpg)


## License

**The script is intended as a joke.**

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.


*Don't use it for stealing bitcoin... in case you have that much computer power use it for something else, something useful.*