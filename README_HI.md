# PIKACHU AI — Android Studio Project

यह PIKACHU AI का शुरुआती Android Studio प्रोजेक्ट है।

## अभी क्या काम करता है
- फोन की Speech Recognition सेवा से हिंदी बोलकर कमांड पहचानना
- कैमरा खोलना
- YouTube खोलना
- WhatsApp खोलना
- फोन डायलर खोलना
- मीडिया वॉल्यूम बढ़ाना/कम करना

## Android Studio में कैसे खोलें
1. ZIP फ़ाइल extract करें।
2. Android Studio खोलें।
3. **Open** चुनें और `PIKACHU_AI_Android_Studio` फ़ोल्डर चुनें।
4. Gradle Sync पूरा होने दें।
5. Redmi फोन को USB से जोड़ें या Emulator चुनें।
6. Run दबाएँ और माइक्रोफोन की अनुमति दें।

## महत्वपूर्ण सीमाएँ
- यह शुरुआती source project है, तैयार APK नहीं।
- अभी "हेलो पिकाचू" कहकर हमेशा बैकग्राउंड में जागना लागू नहीं है।
- स्क्रीन बंद होने पर wake-word के लिए अलग wake-word engine, foreground service, Android permissions और Redmi/HyperOS battery settings पर काम करना होगा।
- ऑफलाइन पहचान डिवाइस पर उपलब्ध offline speech-recognition भाषा पैक पर निर्भर करती है; इसकी गारंटी नहीं।
- ऐप अभी तुम्हारी आवाज़ की क्लोनिंग/नकल नहीं करता। इसके लिए अलग, सहमति-आधारित voice model और voice-output integration चाहिए।
- WhatsApp संदेश भेजने और कॉल करने से पहले उपयोगकर्ता की पुष्टि/चयन जरूरी रखा जाना चाहिए।

## Build settings
- Package: `com.pikachu.ai`
- Minimum Android: 8.0 (API 26)
- Compile/Target SDK: 35
- Kotlin + Android Views
