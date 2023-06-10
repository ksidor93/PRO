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

export type PlannedElements = {
    id: number
    numberOfElements: number
    element: {
        id: number
        name: string
        code: string
        typeOfUnit: string
        quantityInUnit: number
        elementReturnReleases: [number]
        elementInWarehouses: [number]
        elementEvents: [number]
        attachmentId: number
        listOfElementsPlannedNumber: [number]
    }
    orderStageId: number
    demandAdHocId: number
}
