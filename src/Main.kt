class EncodingStringToDNA(val stringToEncoding: String){
    fun stringToTrits(): String {
        return stringToEncoding.toByteArray().joinToString("") {
            (it.toInt() and 0xFF).toString(3).padStart(6, '0')
        }
    }

    fun tritsToNucleotides(dataToConvert: String = stringToTrits()): String{
        val result = StringBuilder()

        val encodingRules = mapOf(
            'A' to "CGT",
            'C' to "GTA",
            'G' to "TAC",
            'T' to "ACG"
        )
        var previousChar = 'A'

        for(char in dataToConvert){
            val digit = char.digitToInt()

            val encodingRule = encodingRules[previousChar]!!

            val newChar = encodingRule[digit]
            result.append(newChar)
            previousChar = newChar
        }
        return result.toString()
    }
}

class DecodingDNAToString(val DNAToDecoding: String){
    fun DNAToTrits(): String{

        val result = StringBuilder()

        val encodingRules = mapOf(
            'A' to "CGT",
            'C' to "GTA",
            'G' to "TAC",
            'T' to "ACG"
        )

        var previousChar = 'A'

        for(nucleotide in DNAToDecoding){
            val encodingRule = encodingRules[previousChar]!!

            val newChar = encodingRule.indexOf(nucleotide)
            result.append(newChar)
            previousChar = nucleotide
        }
        return result.toString()
    }

    fun tritsToText(dataToConvert: String = DNAToTrits()): String{
        val bytesList = dataToConvert.chunked(6).map {
            it.toInt(3).toByte()
        }
        return String(bytesList.toByteArray())
    }
}

fun main(){
    val text = "Ala ma kota"
    val encoder = EncodingStringToDNA(text)
    val encodedDNA = encoder.tritsToNucleotides()
    println("Original: $text")
    println("Trity: ${encoder.stringToTrits()}")
    println("DNA: $encodedDNA")

    val decoder = DecodingDNAToString(encodedDNA)
    val decodedText = decoder.tritsToText()
    println("Decoded Trity: ${decoder.DNAToTrits()}")
    println("Decoded Text: $decodedText")
}