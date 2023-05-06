//
//  SpendingListViewModel.swift
//  iosApp
//
//  Created by Lucy on 06/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine
import KMPNativeCoroutinesCombine

@MainActor class SpendingListViewModel : ObservableObject {
    private let presenter : SpendingListPresenter
    
    @Published var uiState : SpendingListState = SpendingListState(
        spendings: [],
        spendingMarkedForDeletion: [],
        isMarkActive: false,
        isLoading: false
    )
    
    init(presenter : SpendingListPresenter) {
        self.presenter = presenter
        
        createPublisher(for: presenter.uiState)
            .receive(on: DispatchQueue.main)
            .assertNoFailure()
            .eraseToAnyPublisher()
            .assign(to: &$uiState)
    }
    
    func onAddPressed() {
        presenter.onAddTapped()
    }
    
}
