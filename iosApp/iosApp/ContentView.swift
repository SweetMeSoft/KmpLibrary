import UIKit
import SwiftUI
import KmpTestApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard)
            .safeAreaInset(edge: .top, spacing: -8) {
                Color.black.frame(height: 0)
            }
    }
}