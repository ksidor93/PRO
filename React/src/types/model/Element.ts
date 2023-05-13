export type Element = {
    id: number
    name: string
    code: string
    typeOfUnit: string
    quantityInUnit: number
    deleted: boolean
}

export const TypeOfUnit = {
    KILOGRAM: 'Kilogram',
    LITER: 'Litr',
    PIECE: 'Sztuka',
}
